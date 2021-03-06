package com.cognifide.slice.commons.link;

/*
 * #%L
 * Slice - Core
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


import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cognifide.slice.api.link.Link;
import com.cognifide.slice.core.internal.link.LinkImpl;

/**
 * @author Jan Kuźniak
 * 
 */
public class LinkBuilderTest {

	@Test
	public void testEmptyBuilder() {
		LinkBuilderImpl lb = new LinkBuilderImpl();
		lb.setPath("/content");
		assertEquals("", "/content", lb.toString());
		lb.setExtension("html");
		assertEquals("", "/content.html", lb.toString());
		lb.addSelector("desktop");
		assertEquals("", "/content.desktop.html", lb.toString());
		lb.setSuffix("search.html");
		assertEquals("", "/content.desktop.html/search.html", lb.toString());
	}

	@Test
	public void testAddSelector() {
		Link link = new LinkImpl("/content", "search", "html", "test.html");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.addSelector("desktop");
		assertEquals("", "/content.search.desktop.html/test.html", lb.toString());
		// add selector that was already existing
		lb.addSelector("desktop");
		assertEquals("", "/content.search.desktop.html/test.html", lb.toString());
	}

	@Test
	public void testRemoveSelector() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "test.html");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.removeSelector("desktop");
		assertEquals("", "/content.search.html/test.html", lb.toString());
		// remove selector that is not in the link
		lb.removeSelector("desktop");
		assertEquals("", "/content.search.html/test.html", lb.toString());
		// remove last selector
		lb.removeSelector("search");
		assertEquals("", "/content.html/test.html", lb.toString());
	}

	@Test
	public void setPathTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.setPath("/etc/designs");
		assertEquals("",
				"/etc/designs.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void appendToPathTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.appendToPath("cognifide");
		assertEquals("",
				"/content/cognifide.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
		lb = new LinkBuilderImpl(link);
		lb.appendToPath("/cognifide/");
		assertEquals("",
				"/content/cognifide.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void setProtocolDomainTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.setProtocol("http").setDomain("cognifide.com");
		assertEquals(
				"",
				"http://cognifide.com/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
		lb.setProtocol("ftp://");
		assertEquals(
				"",
				"ftp://cognifide.com/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void setSelectorTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.setSelectorString("sel1.sel2");
		assertEquals("", "/content.sel1.sel2.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());

		List<String> selectors = new ArrayList<String>();
		selectors.add("search");
		selectors.add("desktop");
		link = new LinkImpl("/content", "", "html", "keyword.html", "param1=value1&param2=value2",
				"paragraph1");
		lb = new LinkBuilderImpl(link);
		lb.setSelectors(selectors);
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void setExtensionTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.setExtension("jpg");
		assertEquals("", "/content.search.desktop.jpg/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());

		link = new LinkImpl("/content", "search.desktop", "", "keyword.html", "param1=value1&param2=value2",
				"paragraph1");
		lb = new LinkBuilderImpl(link);
		lb.setExtension("html");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void setSuffixTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.setSuffix("image.jpg");
		assertEquals("", "/content.search.desktop.html/image.jpg?param1=value1&param2=value2#paragraph1",
				lb.toString());

		link = new LinkImpl("/content", "search.desktop", "html", "", "param1=value1&param2=value2",
				"paragraph1");
		lb = new LinkBuilderImpl(link);
		lb.setSuffix("keyword.html");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void setQueryStringTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.setQueryString("p1=v1");
		assertEquals("", "/content.search.desktop.html/keyword.html?p1=v1#paragraph1", lb.toString());

		link = new LinkImpl("/content", "search.desktop", "html", "keyword.html", "", "paragraph1");
		lb = new LinkBuilderImpl(link);
		lb.setQueryString("param1=value1&param2=value2");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void setFragmentTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.setFragment("#anchor");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#anchor",
				lb.toString());

		link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "");
		lb = new LinkBuilderImpl(link);
		lb.setFragment("paragraph1");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void addQueryTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.addQuery("param3", "value3");
		assertEquals(
				"",
				"/content.search.desktop.html/keyword.html?param1=value1&param2=value2&param3=value3#paragraph1",
				lb.toString());

		link = new LinkImpl("/content", "search.desktop", "html", "keyword.html", "", "paragraph1");
		lb = new LinkBuilderImpl(link);
		lb.addQuery("param3", "value3");
		assertEquals("", "/content.search.desktop.html/keyword.html?param3=value3#paragraph1", lb.toString());

		link = new LinkImpl("/content", "search.desktop", "html", "keyword.html", "", "paragraph1");
		lb = new LinkBuilderImpl(link);
		lb.addQuery("", "value3");
		assertEquals("", "/content.search.desktop.html/keyword.html#paragraph1", lb.toString());
		lb.addQuery("param3", "");
		assertEquals("", "/content.search.desktop.html/keyword.html?param3=#paragraph1", lb.toString());

		link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		lb = new LinkBuilderImpl(link);
		lb.addQuery("param1", "value1");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());
		lb.addQuery("param1", "value2");
		assertEquals(
				"",
				"/content.search.desktop.html/keyword.html?param1=value1&param1=value2&param2=value2#paragraph1",
				lb.toString());
	}

	@Test
	public void removeQueryTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.removeQuery("param3");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value1&param2=value2#paragraph1",
				lb.toString());

		lb.removeQuery("param1");
		assertEquals("", "/content.search.desktop.html/keyword.html?param2=value2#paragraph1", lb.toString());

		lb.removeQuery("param2");
		assertEquals("", "/content.search.desktop.html/keyword.html#paragraph1", lb.toString());
	}

	@Test
	public void removeQueryWithValueTest() {
		Link link = new LinkImpl("/content", "search.desktop", "html", "keyword.html",
				"param1=value1&param2=value2&param1=value2", "paragraph1");
		LinkBuilderImpl lb = new LinkBuilderImpl(link);
		lb.removeQuery("param1", "non-exisiting-value");
		assertEquals(
				"",
				"/content.search.desktop.html/keyword.html?param1=value1&param1=value2&param2=value2#paragraph1",
				lb.toString());

		lb.removeQuery("param1", "value1");
		assertEquals("", "/content.search.desktop.html/keyword.html?param1=value2&param2=value2#paragraph1",
				lb.toString());

		lb.removeQuery("param1", "value2");
		assertEquals("", "/content.search.desktop.html/keyword.html?param2=value2#paragraph1", lb.toString());
	}
}
