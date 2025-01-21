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

import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.lang.PropertiesHelper;

/**
 * Contains the version constants of phive-binary.
 *
 * @author Philip Helger
 */
@Immutable
public final class CPhiveBinaryVersion
{
  /** Current version - from properties file */
  public static final String BUILD_VERSION;
  /** Build timestamp - from properties file */
  public static final String BUILD_TIMESTAMP;

  private static final Logger LOGGER = LoggerFactory.getLogger (CPhiveBinaryVersion.class);

  static
  {
    String sProjectVersion = null;
    String sProjectTimestamp = null;
    final ICommonsMap <String, String> p = PropertiesHelper.loadProperties (new ClassPathResource ("phive-binary-version.properties"));
    if (p != null)
    {
      sProjectVersion = p.get ("version");
      sProjectTimestamp = p.get ("timestamp");
    }
    if (sProjectVersion == null)
    {
      sProjectVersion = "undefined";
      LOGGER.warn ("Failed to load phive-binary version number");
    }
    BUILD_VERSION = sProjectVersion;
    if (sProjectTimestamp == null)
    {
      sProjectTimestamp = "undefined";
      LOGGER.warn ("Failed to load phive-binary build timestamp");
    }
    BUILD_TIMESTAMP = sProjectTimestamp;
  }

  private CPhiveBinaryVersion ()
  {}
}
