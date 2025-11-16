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
package com.helger.phive.binary.impl;

import java.util.Map;

import org.jspecify.annotations.NonNull;

import com.helger.base.array.ArrayHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.mime.CMimeType;
import com.helger.phive.binary.AbstractFileFormatDescriptor;
import com.helger.phive.binary.EPhiveContentValidationMode;
import com.helger.phive.binary.IPhiveContentValidator;

/**
 * File format descriptor for Excel XLS files
 *
 * @author Philip Helger
 */
public class FileFormatDescriptorXLS extends AbstractFileFormatDescriptor
{
  public static final String NAME = "Excel Document (before 2007)";
  private static final byte [] MIME_ID_XLS = { (byte) 0xD0, (byte) 0xcd, 0x11, (byte) 0xe0 };

  @NonNull
  private static Map <EPhiveContentValidationMode, IPhiveContentValidator> _getContentValidators ()
  {
    final Map <EPhiveContentValidationMode, IPhiveContentValidator> ret = new CommonsHashMap <> ();
    ret.put (EPhiveContentValidationMode.LEADING_BYTES, data -> ArrayHelper.startsWith (data, MIME_ID_XLS));
    return ret;
  }

  public FileFormatDescriptorXLS ()
  {
    super (NAME,
           "XLS",
           new CommonsArrayList <> ("xls"),
           new CommonsArrayList <> (CMimeType.APPLICATION_MS_EXCEL.getAsString ()),
           _getContentValidators ());
  }
}
