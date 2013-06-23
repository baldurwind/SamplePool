package com.simple.client;

import org.osoa.sca.annotations.Remotable;


@Remotable
public interface TBClient {
	public void execute(String name);
}
