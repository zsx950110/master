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
package com.codingapi.txlcn.tm.support.db.domain;

import com.codingapi.txlcn.txmsg.params.TxExceptionParams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Description:
 * Date: 2018/12/18
 *
 * @author ujued
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_tx_exception")
public class TxException {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 事务组ID
     */
    @Column(length = 60)
    private String groupId;

    /**
     * 事务单元ID
     */
    @Column(length = 60)
    private String unitId;

    /**
     * 资源管理服务地址
     */
    @Column(length = 100)
    private String modId;

    /**
     * 事务状态
     */
    private Integer transactionState;

    /**
     * 上报方
     * @see TxExceptionParams
     */
    private short registrar;

    /**
     * 异常状态 0 待处理 1已处理
     */
    private short exState;

    /**
     * 发生时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public Integer getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(Integer transactionState) {
        this.transactionState = transactionState;
    }

    public short getRegistrar() {
        return registrar;
    }

    public void setRegistrar(short registrar) {
        this.registrar = registrar;
    }

    public short getExState() {
        return exState;
    }

    public void setExState(short exState) {
        this.exState = exState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
