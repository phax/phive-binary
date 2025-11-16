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

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;

/**
 * This enum defines how content validation should take place
 *
 * @author Philip Helger
 */
public enum EPhiveContentValidationMode implements IHasID <String>
{
  /**
   * The file format is detected based on the first n bytes of the content. This
   * is usually a very efficient way of checking but has smaller accuracy.
   */
  LEADING_BYTES ("leading-bytes"),

  /**
   * The file format is detected by doing a full parse of the document to verify
   * its correctness. This is usually more time consuming then
   * {@link #LEADING_BYTES} but also finds more errors.
   */
  FULL_PARSE ("full-parse");

  private final String m_sID;

  EPhiveContentValidationMode (@NonNull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public static EPhiveContentValidationMode getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EPhiveContentValidationMode.class, sID);
  }
}
