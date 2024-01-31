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
package com.helger.phive.binary.impl;

import java.util.Map;

import javax.annotation.Nonnull;

import com.helger.commons.collection.ArrayHelper;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.mime.CMimeType;
import com.helger.phive.binary.AbstractFileFormatDescriptor;
import com.helger.phive.binary.EPhiveContentValidationMode;
import com.helger.phive.binary.IPhiveContentValidator;

/**
 * File format descriptor for Excel XLSX files
 *
 * @author Philip Helger
 */
public class FileFormatDescriptorXLSX extends AbstractFileFormatDescriptor
{
  public static final String NAME = "Excel Document";
  private static final byte [] MIME_ID_XLSX = { 0x50, 0x4b, 0x03, 0x04 };

  @Nonnull
  private static Map <EPhiveContentValidationMode, IPhiveContentValidator> _getContentValidators ()
  {
    final Map <EPhiveContentValidationMode, IPhiveContentValidator> ret = new CommonsHashMap <> ();
    ret.put (EPhiveContentValidationMode.LEADING_BYTES, data -> ArrayHelper.startsWith (data, MIME_ID_XLSX));
    return ret;
  }

  public FileFormatDescriptorXLSX ()
  {
    super (NAME,
           "XLSX",
           new CommonsArrayList <> ("xlsx"),
           new CommonsArrayList <> (CMimeType.APPLICATION_MS_EXCEL_2007.getAsString ()),
           _getContentValidators ());
  }
}
