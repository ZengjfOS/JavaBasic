package com.zengjf;

import static org.junit.Assert.assertTrue;

import com.dns.DnsItem;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void runDefaultApp()
    {
        String args[] = {"zengjf"};
        App.main(args);
    }

    @Test
    public void showHelloWorld()
    {
        DnsItem dnsItem = new DnsItem();
        dnsItem.dns = "zengjf.com";
        dnsItem.addr = "127.0.0.1";

        System.out.println(dnsItem);

        new App();
    }
}
