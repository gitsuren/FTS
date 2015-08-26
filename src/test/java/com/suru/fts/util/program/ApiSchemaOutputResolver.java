package com.suru.fts.util.program;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import com.suru.fts.api.dto.FeaturesForIdResponse;

public class ApiSchemaOutputResolver extends SchemaOutputResolver {

	@Override
	public Result createOutput(String namespaceUri, String suggestedFileName)throws IOException {
		
		File file = new File("generated/fts-api.xsd");
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        return result;
	}

	public static void main(String[] args) throws Exception {
		
		Class<?>[] classes = new Class[1];
		classes[0] = FeaturesForIdResponse.class;
		
		JAXBContext jaxbContext = JAXBContext.newInstance(classes);

		SchemaOutputResolver sor = new ApiSchemaOutputResolver();
		jaxbContext.generateSchema(sor);
	}
}
