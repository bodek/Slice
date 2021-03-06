package com.cognifide.slice.cq.mapper;

/*
 * #%L
 * Slice - CQ Add-on
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.cognifide.slice.cq.mapper.processor.ImageFieldProcessor;
import com.cognifide.slice.mapper.api.Mapper;
import com.cognifide.slice.mapper.api.SlingMapperFactory;
import com.google.inject.Inject;

public class CQMapperFactory {

	private final SlingMapperFactory slingMapperFactory;

	@Inject
	public CQMapperFactory(final SlingMapperFactory slingMapperFactory) {
		this.slingMapperFactory = slingMapperFactory;
	}

	public Mapper getMapper() {
		final Mapper mapper = slingMapperFactory.getMapper();
		mapper.registerFieldProcessor(new ImageFieldProcessor());
		return mapper;
	}

}
