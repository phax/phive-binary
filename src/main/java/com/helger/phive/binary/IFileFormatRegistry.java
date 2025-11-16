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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.state.ESuccess;
import com.helger.collection.commons.ICommonsOrderedMap;
import com.helger.mime.IMimeType;

/**
 * Base interface for a file format registry
 *
 * @author Philip Helger
 */
public interface IFileFormatRegistry
{
  /**
   * Register a new file format descriptor into the registry. Implementations
   * must check the uniqueness of file extensions and MIME types.
   *
   * @param aDescriptor
   *        The descriptor to be registered. May not be <code>null</code>.
   * @return {@link ESuccess#SUCCESS} in case the new file format was
   *         registered, {@link ESuccess#FAILURE} if not. Never
   *         <code>null</code>.
   */
  @NonNull
  ESuccess registerFileFormat (@NonNull IFileFormatDescriptor aDescriptor);

  /**
   * @return A map with all registered file format descriptors, with the file
   *         format name as the key.
   */
  @NonNull
  @ReturnsMutableCopy
  ICommonsOrderedMap <String, IFileFormatDescriptor> getAllFileFormatDescriptors ();

  /**
   * Find the file format descriptor with the given name
   *
   * @param sName
   *        The name of the file format descriptor to use. May be
   *        <code>null</code>.
   * @return <code>null</code> if no such file format descriptor exists
   * @since 0.1.1
   */
  @Nullable
  IFileFormatDescriptor getFileFormatDescriptorOfName (@Nullable String sName);

  /**
   * Find the file format descriptor with the given file extension
   *
   * @param sFileExt
   *        The file extension of the file format descriptor to use. Must be all
   *        lowercase and not have a leading dot. May be <code>null</code>.
   * @return <code>null</code> if no such file format descriptor exists
   */
  @Nullable
  IFileFormatDescriptor getFileFormatDescriptorByFileExtension (@Nullable String sFileExt);

  /**
   * Find the file format descriptor with the given MIME type
   *
   * @param sMimeType
   *        The MIME type of the file format descriptor to use. Must be all
   *        lowercase and not have any parameters. May be <code>null</code>.
   * @return <code>null</code> if no such file format descriptor exists
   */
  @Nullable
  IFileFormatDescriptor getFileFormatDescriptorByMimeType (@Nullable String sMimeType);

  /**
   * Find the file format descriptor with the given MIME type
   *
   * @param aMimeType
   *        The MIME type of the file format descriptor to use. May be
   *        <code>null</code>.
   * @return <code>null</code> if no such file format descriptor exists
   */
  @Nullable
  default IFileFormatDescriptor getFileFormatDescriptorByMimeType (@Nullable final IMimeType aMimeType)
  {
    return aMimeType == null ? null : getFileFormatDescriptorByMimeType (aMimeType.getAsStringWithoutParameters ());
  }
}
