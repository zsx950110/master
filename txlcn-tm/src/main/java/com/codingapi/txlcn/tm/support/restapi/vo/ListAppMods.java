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

import lombok.Data;

import java.util.List;

/**
 * Description:
 * Date: 19-1-17 上午11:56
 *
 * @author ujued
 */
@Data
public class ListAppMods {
    private long total;
    private List<AppMod> appMods;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<AppMod> getAppMods() {
        return appMods;
    }

    public void setAppMods(List<AppMod> appMods) {
        this.appMods = appMods;
    }

    @Data
    public static class AppMod {
        private String modName;
        private String modId;
        private String registerTime;

        public String getModName() {
            return modName;
        }

        public void setModName(String modName) {
            this.modName = modName;
        }

        public String getModId() {
            return modId;
        }

        public void setModId(String modId) {
            this.modId = modId;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }
    }
}
