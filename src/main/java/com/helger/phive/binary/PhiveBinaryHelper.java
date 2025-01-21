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

import java.util.Locale;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.mime.MimeType;
import com.helger.commons.mime.MimeTypeParser;
import com.helger.commons.string.StringHelper;

/**
 * Helper classes
 *
 * @author Philip Helger
 */
@Immutable
public final class PhiveBinaryHelper
{
  private PhiveBinaryHelper ()
  {}

  /**
   * Check if the provided file extension is valid or not. Valid file extensions
   * are non-empty, don't start with a dot and are all-lowercase.
   *
   * @param s
   *        The file extension to check. May be <code>null</code>.
   * @return <code>true</code> if it is valid, <code>false</code> if not.
   */
  public static boolean isValidFileExtension (@Nullable final String s)
  {
    return StringHelper.hasText (s) && s.charAt (0) != '.' && s.toLowerCase (Locale.ROOT).equals (s);
  }

  /**
   * Check if the provided MIME Type is valid or not. Valid MIME Types are
   * non-empty, are all-lowercase and contain no parameters.
   *
   * @param s
   *        The MIME type to check. May be <code>null</code>.
   * @return <code>true</code> if it is valid, <code>false</code> if not.
   */
  public static boolean isValidMimeType (@Nullable final String s)
  {
    if (StringHelper.hasText (s) && s.toLowerCase (Locale.ROOT).equals (s))
    {
      final MimeType aMimeType = MimeTypeParser.safeParseMimeType (s);
      if (aMimeType != null)
        return !aMimeType.hasAnyParameters ();
    }
    return false;
  }
}
