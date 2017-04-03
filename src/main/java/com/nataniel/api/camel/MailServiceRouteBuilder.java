package com.nataniel.api.camel;

import com.nataniel.api.BadRequestException;
import com.nataniel.api.JsonRequestProcessor;
import javassist.NotFoundException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.common.i18n.Exception;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;

public class MailServiceRouteBuilder extends RouteBuilder {

	@Autowired
	private JsonRequestProcessor jsonRequestProcessor;

	@Override
	public void configure() throws Exception {
		onException(BadRequestException.class).handled(true).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
				Response response = Response.status(400).entity("{\"error\":\"" + caused.getMessage() + "\"}").build();
				exchange.getOut().setBody(response);
				exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
			}
		});

		onException(NotFoundException.class).handled(true).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				Throwable caused = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
				Response r = Response.status(404).entity("{\"error\":\"" + caused.getMessage() + "\"}").build();
				exchange.getOut().setBody(r);
				exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE);
			}
		});

		configureOrderService();
	}

	void configureOrderService() {
		from("cxfrs:bean:productOrder")
				.recipientList(simple("direct:${header.operationName}")).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().removeHeader("Content-Length");
			}
		});

		from("direct:createProductOrder")
				.process(jsonRequestProcessor)
				.to("class:com.nataniel.api.services.ProductOrderService?method=createProductOrder(*)");

		from("direct:getAllOrders")
				.to("bean:productOrderService?method=getAllOrders()");

		from("direct:getOrderByControlNumber")
				.to("bean:productOrderService?method=getOrderByControlNumber()");

		from("direct:getOrderByRegistrationDate")
				.to("bean:productOrderService?method=getOrderByRegistrationDate()");
	}
}