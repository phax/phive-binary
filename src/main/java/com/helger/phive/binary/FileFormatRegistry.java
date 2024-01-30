/*
 * Copyright (C) 2024 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.phive.binary;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.CommonsLinkedHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.collection.impl.ICommonsOrderedMap;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.lang.ServiceLoaderHelper;
import com.helger.commons.state.ESuccess;
import com.helger.commons.string.ToStringGenerator;

/**
 * This is a central file format registry that should be used to started
 * detection processes.
 *
 * @author Philip Helger
 */
@ThreadSafe
public class FileFormatRegistry implements IFileFormatRegistry
{
  private static final Logger LOGGER = LoggerFactory.getLogger (FileFormatRegistry.class);

  private static final class SingletonHolder
  {
    private static final FileFormatRegistry INSTANCE = new FileFormatRegistry ();
  }

  private static boolean s_bDefaultInstantiated = false;

  private final SimpleReadWriteLock m_aRWLock = new SimpleReadWriteLock ();
  @GuardedBy ("m_aRWLock")
  private final ICommonsOrderedMap <String, IFileFormatDescriptor> m_aNameMap = new CommonsLinkedHashMap <> ();
  @GuardedBy ("m_aRWLock")
  private final ICommonsMap <String, IFileFormatDescriptor> m_aFileExtMap = new CommonsHashMap <> ();
  @GuardedBy ("m_aRWLock")
  private final ICommonsMap <String, IFileFormatDescriptor> m_aMimeTypeMap = new CommonsHashMap <> ();

  private FileFormatRegistry ()
  {
    _reinitialize ();
  }

  public static boolean isInstantiated ()
  {
    return s_bDefaultInstantiated;
  }

  @Nonnull
  public static FileFormatRegistry getInstance ()
  {
    final FileFormatRegistry ret = SingletonHolder.INSTANCE;
    s_bDefaultInstantiated = true;
    return ret;
  }

  @Nonnull
  public ESuccess registerFileFormat (@Nonnull final IFileFormatDescriptor aDescriptor)
  {
    ValueEnforcer.notNull (aDescriptor, "Descriptor");

    return m_aRWLock.writeLockedGet ( () -> {
      // 1. check name
      final String sName = aDescriptor.getName ();
      if (m_aNameMap.containsKey (sName))
      {
        LOGGER.error ("A file format with name '" + sName + "' is already registered");
        return ESuccess.FAILURE;
      }

      // 2. check file extensions
      for (final String sFileExt : aDescriptor.getAllAllowedFileExtensions ())
        if (m_aFileExtMap.containsKey (sFileExt))
        {
          LOGGER.error ("The file extension '" + sFileExt + "' is already registered");
          return ESuccess.FAILURE;
        }

      // 3. check MIME types
      for (final String sMimeType : aDescriptor.getAllAllowedMimeTypes ())
        if (m_aMimeTypeMap.containsKey (sMimeType))
        {
          LOGGER.error ("The MIME type '" + sMimeType + "' is already registered");
          return ESuccess.FAILURE;
        }

      LOGGER.info ("Registering File Format Descriptor for format '" +
                   sName +
                   "' (" +
                   aDescriptor.getShortName () +
                   ")");

      // Now remember mappings
      m_aNameMap.put (sName, aDescriptor);
      for (final String sFileExt : aDescriptor.getAllAllowedFileExtensions ())
        m_aFileExtMap.put (sFileExt, aDescriptor);
      for (final String sMimeType : aDescriptor.getAllAllowedMimeTypes ())
        m_aMimeTypeMap.put (sMimeType, aDescriptor);
      return ESuccess.SUCCESS;
    });
  }

  @Nonnull
  @ReturnsMutableCopy
  public final ICommonsOrderedMap <String, IFileFormatDescriptor> getAllFileFormatDescriptors ()
  {
    return m_aRWLock.readLockedGet ( () -> m_aNameMap.getClone ());
  }

  @Nullable
  public final IFileFormatDescriptor getFileFormatDescriptorByFileExtension (@Nullable final String sFileExt)
  {
    if (PhiveBinaryHelper.isValidFileExtension (sFileExt))
      return m_aRWLock.readLockedGet ( () -> m_aFileExtMap.get (sFileExt));
    return null;
  }

  @Nullable
  public final IFileFormatDescriptor getFileFormatDescriptorByMimeType (@Nullable final String sMimeType)
  {
    if (PhiveBinaryHelper.isValidMimeType (sMimeType))
      return m_aRWLock.readLockedGet ( () -> m_aMimeTypeMap.get (sMimeType));
    return null;
  }

  @Nonnegative
  public final int getRegisteredFileFormatDescriptorCount ()
  {
    return m_aRWLock.readLockedInt ( () -> m_aNameMap.size ());
  }

  private void _reinitialize ()
  {
    m_aRWLock.writeLocked ( () -> {
      m_aNameMap.clear ();
      m_aFileExtMap.clear ();
      m_aMimeTypeMap.clear ();

      // Register all custom type converter.
      // Must be in writeLock to ensure no reads happen during initialization
      for (final IFileFormatRegistrarSPI aSPI : ServiceLoaderHelper.getAllSPIImplementations (IFileFormatRegistrarSPI.class))
      {
        if (LOGGER.isDebugEnabled ())
          LOGGER.debug ("Calling registerTypeConverter on " + aSPI.getClass ().getName ());
        aSPI.registerFileFormats (this);
      }
    });

    if (LOGGER.isDebugEnabled ())
      LOGGER.debug (getRegisteredFileFormatDescriptorCount () + " file formats registered");
  }

  public void reinitialize ()
  {
    if (LOGGER.isDebugEnabled ())
      LOGGER.debug ("Reinitializing " + getClass ().getName ());

    _reinitialize ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("Descriptors", m_aNameMap)
                                       .append ("FileExtCount", m_aFileExtMap.size ())
                                       .append ("MimeTypeCount", m_aMimeTypeMap.size ())
                                       .getToString ();
  }
}
