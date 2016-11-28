package com.nataniel.api.camel;

import com.nataniel.api.JsonRequestProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.common.i18n.Exception;
import org.springframework.beans.factory.annotation.Autowired;

public class MailServiceRouteBuilder extends RouteBuilder {

	@Autowired
	private JsonRequestProcessor jsonRequestProcessor;

	@Override
	public void configure() throws Exception {
		configureUserService();
	}

	void configureUserService() {
		from("cxfrs:bean:user")
				.recipientList(simple("direct:${header.operationName}")).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().removeHeader("Content-Length");
			}
		});

		from("direct:listUsers")
				.to("class:com.nataniel.api.services.UserService?method=listUsers(*)");

		from("direct:createUser")
				.process(jsonRequestProcessor)
				.to("class:com.nataniel.api.services.UserService?method=createUser(*)");
	}
}