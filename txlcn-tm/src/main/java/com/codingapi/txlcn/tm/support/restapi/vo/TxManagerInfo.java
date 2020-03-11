/*
 * Copyright 2017-2019 CodingApi .
 *
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
 */
package com.codingapi.txlcn.tm.support.restapi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Date: 2018/12/28
 *
 * @author ujued
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TxManagerInfo {

    /**
     * Netty主机
     */
    private String socketHost;

    /**
     * Netty端口
     */
    private int socketPort;

    /**
     * Netty心跳时间
     */
    private long heartbeatTime;

    /**
     * 注册的资源管理服务数量
     */
    private int clientCount;

    /**
     * 事务并发处理等级
     */
    private int concurrentLevel;

    /**
     * 分布式事务时间
     */
    private long dtxTime;

    /**
     * 异常通知
     */
    private String exUrl;

    /**
     * 允许系统日志
     */
    private String enableTxLogger;

    /**
     * TM Version
     */
    private String tmVersion;

    public String getSocketHost() {
        return socketHost;
    }

    public void setSocketHost(String socketHost) {
        this.socketHost = socketHost;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public long getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(long heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public int getClientCount() {
        return clientCount;
    }

    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }

    public int getConcurrentLevel() {
        return concurrentLevel;
    }

    public void setConcurrentLevel(int concurrentLevel) {
        this.concurrentLevel = concurrentLevel;
    }

    public long getDtxTime() {
        return dtxTime;
    }

    public void setDtxTime(long dtxTime) {
        this.dtxTime = dtxTime;
    }

    public String getExUrl() {
        return exUrl;
    }

    public void setExUrl(String exUrl) {
        this.exUrl = exUrl;
    }

    public String getEnableTxLogger() {
        return enableTxLogger;
    }

    public void setEnableTxLogger(String enableTxLogger) {
        this.enableTxLogger = enableTxLogger;
    }

    public String getTmVersion() {
        return tmVersion;
    }

    public void setTmVersion(String tmVersion) {
        this.tmVersion = tmVersion;
    }
}
