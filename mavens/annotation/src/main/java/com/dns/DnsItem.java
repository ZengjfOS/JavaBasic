package com.dns;

/**
 * <ul>
 *   <li>格式可以参考：https://github.com/imp/dnsmasq/blob/master/dnsmasq.conf.example:
 *   <li>使用域名白名单，即server配置，故需要域名及其对应的域名解析服务器IP
 *   <li>每个国家电信网络都有自己的域名解析服务器IP，有一些企业也会维护域名解析服务器IP，例如Google的8.8.8.8
 * </ul>
 */
public class DnsItem {
    /**
     * 设置的域名
     */
    public String dns;
    /**
     * 域名对应域名解析服务器ip
     */
    public String addr;
}
