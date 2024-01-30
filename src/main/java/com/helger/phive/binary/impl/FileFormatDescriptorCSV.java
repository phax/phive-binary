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

import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.mime.CMimeType;
import com.helger.phive.binary.AbstractFileFormatDescriptor;

/**
 * File format descriptor for CSV
 *
 * @author Philip Helger
 */
public class FileFormatDescriptorCSV extends AbstractFileFormatDescriptor
{
  public FileFormatDescriptorCSV ()
  {
    super ("Comma-separated values",
           "CSV",
           new CommonsArrayList <> ("csv"),
           new CommonsArrayList <> (CMimeType.TEXT_CSV.getAsStringWithoutParameters ()),
           new CommonsHashMap <> ());
  }
}
