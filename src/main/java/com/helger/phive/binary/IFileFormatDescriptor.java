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

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.name.IHasName;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsSet;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Descriptor for a single file format.
 *
 * @author Philip Helger
 */
public interface IFileFormatDescriptor extends IHasName
{
  /**
   * @return The short name of the file format. Defaults to the default name.
   *         E.g. "PDF" would be the short name for "Portable File Format".
   */
  @Nonnull
  @Nonempty
  String getShortName ();

  /**
   * @return A set with all allowed file extensions for this file format. Each
   *         extension does NOT contain the leading dot (as in
   *         <code>pdf</code>). Each file extension must be in all lowercase
   *         characters. Each file extension must be assigned to exactly one
   *         file format only. Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  ICommonsSet <String> getAllAllowedFileExtensions ();

  /**
   * @return A set with all allowed MIME types for this file format. Each MIME
   *         type must be in all lowercase characters. The MIME type must not
   *         contain any parameters (as e.g. in <code>; charset=utf-8</code>).
   *         Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  ICommonsSet <String> getAllAllowedMimeTypes ();

  /**
   * @return A map with all registered content validators for this file format.
   *         Not all file formats support one or all detection modes. Never
   *         <code>null</code> but maybe empty.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsMap <EPhiveContentValidationMode, IPhiveContentValidator> getAllContentValidators ();

  /**
   * Find the best matching content validator, based on the provided validation
   * modes.
   *
   * @param aModes
   *        The array of detection modes that are tried linearly until the first
   *        match is found.
   * @return <code>null</code> if no mode was provided, or this file format does
   *         not contain a detector for the provided modes.
   */
  @Nullable
  IPhiveContentValidator findContentValidator (@Nullable EPhiveContentValidationMode... aModes);

  /**
   * @return The first provided content validator, with speed prioritized over
   *         accuracy. May be <code>null</code>.
   */
  @Nullable
  default IPhiveContentValidator getContentValidatorFavourSpeed ()
  {
    return findContentValidator (EPhiveContentValidationMode.LEADING_BYTES, EPhiveContentValidationMode.FULL_PARSE);
  }

  /**
   * @return The first provided content validator, with accuracy prioritized
   *         over speed. May be <code>null</code>.
   */
  @Nullable
  default IPhiveContentValidator getContentValidatorFavourAccuracy ()
  {
    return findContentValidator (EPhiveContentValidationMode.FULL_PARSE, EPhiveContentValidationMode.LEADING_BYTES);
  }
}
