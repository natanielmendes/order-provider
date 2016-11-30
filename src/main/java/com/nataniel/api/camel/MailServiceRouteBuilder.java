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
		configureRateService();
		configureMessageService();
		configureFriendsService();
	}

	void configureUserService() {
		from("cxfrs:bean:user")
				.recipientList(simple("direct:${header.operationName}")).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().removeHeader("Content-Length");
			}
		});

		from("direct:createUser")
				.process(jsonRequestProcessor)
				.to("class:com.nataniel.api.services.UserService?method=createUser(*)");

		from("direct:login")
				.process(jsonRequestProcessor)
				.to("class:com.nataniel.api.services.UserService?method=login(*)");

		from("direct:getAllUsers")
				.to("bean:userService?method=getAllUsers()");

		from("direct:getUserById")
				.to("bean:userService?method=getUserById(${header.userId})");

		from("direct:updateUserById")
				.process(jsonRequestProcessor)
				.to("bean:userService?method=updateUserById(*)");
	}

	void configureRateService() {
		from("cxfrs:bean:rate")
				.recipientList(simple("direct:${header.operationName}")).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().removeHeader("Content-Length");
			}
		});

		from("direct:rateUser")
				.process(jsonRequestProcessor)
				.to("class:com.nataniel.api.services.RateService?method=rateUser(*)");

		from("direct:getRate")
				.to("class:com.nataniel.api.services.RateService?method=getRate(${header.userIdTo})");
	}

	void configureMessageService() {
		from("cxfrs:bean:message")
				.recipientList(simple("direct:${header.operationName}")).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().removeHeader("Content-Length");
			}
		});

		from("direct:postMessage")
				.process(jsonRequestProcessor)
				.to("class:com.nataniel.api.services.MessageService?method=postMessage(*)");

		from("direct:getMessages")
				.process(jsonRequestProcessor)
				.to("bean:messageService?method=getMessages(*)");
	}

	void configureFriendsService() {
		from("cxfrs:bean:friend")
				.recipientList(simple("direct:${header.operationName}")).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().removeHeader("Content-Length");
			}
		});

		from("direct:getFriends")
				.to("bean:friendService?method=getFriends(${header.userIdFrom})");
	}
}