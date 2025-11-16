/*
 * Copyright (C) 2024-2025 Philip Helger (www.helger.com)
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

import java.util.Collection;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.CommonsHashSet;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsSet;

/**
 * Abstract implementation of {@link IFileFormatDescriptor}.
 *
 * @author Philip Helger
 */
public abstract class AbstractFileFormatDescriptor implements IFileFormatDescriptor
{
  private final String m_sName;
  private final String m_sShortName;
  private final ICommonsSet <String> m_aAllowedFileExtensions;
  private final ICommonsSet <String> m_aAllowedMimeTypes;
  private final ICommonsMap <EPhiveContentValidationMode, IPhiveContentValidator> m_aContentValidators;

  protected AbstractFileFormatDescriptor (@NonNull @Nonempty final String sName,
                                          @Nullable final String sShortName,
                                          @NonNull @Nonempty final Collection <String> aAllowedFileExtensions,
                                          @NonNull @Nonempty final Collection <String> aAllowedMimeTypes,
                                          @NonNull final Map <EPhiveContentValidationMode, IPhiveContentValidator> aContentValidators)
  {
    if (ValueEnforcer.isEnabled ())
    {
      ValueEnforcer.notEmpty (sName, "Name");
      ValueEnforcer.notEmpty (aAllowedFileExtensions, "AllowedFileExtensions");
      for (final String sFileExtension : aAllowedFileExtensions)
        ValueEnforcer.isTrue ( () -> PhiveBinaryHelper.isValidFileExtension (sFileExtension),
                               () -> "File Extension '" + sFileExtension + "' is invalid");
      ValueEnforcer.notEmpty (aAllowedMimeTypes, "AllowedMimeTypes");
      for (final String sMimeType : aAllowedMimeTypes)
        ValueEnforcer.isTrue ( () -> PhiveBinaryHelper.isValidMimeType (sMimeType),
                               () -> "MIME Type '" + sMimeType + "' is invalid");
      ValueEnforcer.notNullNoNullValue (aContentValidators, "ContentValidators");
    }

    m_sName = sName;
    m_sShortName = StringHelper.getNotEmpty (sShortName, sName);
    m_aAllowedFileExtensions = new CommonsHashSet <> (aAllowedFileExtensions);
    m_aAllowedMimeTypes = new CommonsHashSet <> (aAllowedMimeTypes);
    m_aContentValidators = new CommonsHashMap <> (aContentValidators);
  }

  @NonNull
  @Nonempty
  public final String getName ()
  {
    return m_sName;
  }

  @NonNull
  @Nonempty
  public final String getShortName ()
  {
    return m_sShortName;
  }

  @NonNull
  @Nonempty
  @ReturnsMutableObject
  protected final ICommonsSet <String> allowedFileExtensions ()
  {
    return m_aAllowedFileExtensions;
  }

  @NonNull
  @Nonempty
  @ReturnsMutableCopy
  public final ICommonsSet <String> getAllAllowedFileExtensions ()
  {
    return m_aAllowedFileExtensions.getClone ();
  }

  @NonNull
  @Nonempty
  @ReturnsMutableObject
  protected final ICommonsSet <String> internalAllowedMimeTypes ()
  {
    return m_aAllowedMimeTypes;
  }

  @NonNull
  @Nonempty
  @ReturnsMutableCopy
  public final ICommonsSet <String> getAllAllowedMimeTypes ()
  {
    return m_aAllowedMimeTypes.getClone ();
  }

  @NonNull
  @ReturnsMutableCopy
  protected final ICommonsMap <EPhiveContentValidationMode, IPhiveContentValidator> internalContentDetectors ()
  {
    return m_aContentValidators;
  }

  @NonNull
  @ReturnsMutableCopy
  public final ICommonsMap <EPhiveContentValidationMode, IPhiveContentValidator> getAllContentValidators ()
  {
    return m_aContentValidators.getClone ();
  }

  @Nullable
  public IPhiveContentValidator findContentValidator (@Nullable final EPhiveContentValidationMode... aModes)
  {
    if (aModes != null)
      for (final EPhiveContentValidationMode eMode : aModes)
      {
        final IPhiveContentValidator ret = m_aContentValidators.get (eMode);
        if (ret != null)
          return ret;
      }
    return null;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("Name", m_sName)
                                       .append ("ShortName", m_sShortName)
                                       .append ("AllowedFileExtensions", m_aAllowedFileExtensions)
                                       .append ("AllowedMimeTypes", m_aAllowedMimeTypes)
                                       .append ("ContentValidators", m_aContentValidators)
                                       .getToString ();
  }
}
