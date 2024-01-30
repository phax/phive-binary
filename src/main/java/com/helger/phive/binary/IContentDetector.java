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

import javax.annotation.Nonnull;

/**
 * A single content detector interface
 *
 * @author Philip Helger
 */
@FunctionalInterface
public interface IContentDetector
{
  /**
   * Check if the provided data matches the requirements.
   *
   * @param aData
   *        the data to check
   * @return <code>true</code> if the expected content was detected,
   *         <code>false</code> if not.
   */
  boolean matchesContent (@Nonnull byte [] aData);
}