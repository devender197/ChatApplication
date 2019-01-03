package com.example.a3logics.chatapplication.AudioRecordingButton;

import java.util.List;

public class temp {

    /**
     * response : {"responseHeader":{"error":false,"errorCode":null,"errorShortDesc":null,"asOfDate":1542714645429,"errorLongDesc":null,"fromCache":false,"cacheType":"t60Cache"},"results":[{"RowNumber":3,"instrumentId":3022906,"instrumentType":"Stock","symbol":"GABRIEL","usSymbol":"GAB.IN","name":"Gabriel India","traditionalChineseName":"Gabriel India","localName":"Gabriel India","active":"true","formattedDescription":"NULL","industryName":"Auto/Truck-Original Eqp","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3028705,"instrumentType":"Stock","symbol":"GAEL","usSymbol":"GAE.IN","name":"Gujarat Ambuja Exports","traditionalChineseName":"Gujarat Ambuja Exports","localName":"Gujarat Ambuja Exports","active":"true","formattedDescription":"NULL","industryName":"Food-Packaged","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3022903,"instrumentType":"Stock","symbol":"GAIL","usSymbol":"GAI.IN","name":"Gail (India)","traditionalChineseName":"Gail (India)","localName":"Gail (India)","active":"true","formattedDescription":"NULL","industryName":"Utility-Gas Distribution","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3114376,"instrumentType":"Stock","symbol":"GAL","usSymbol":"YSG.IN","name":"Gyscoal Alloys","traditionalChineseName":"Gyscoal Alloys","localName":"Gyscoal Alloys","active":"true","formattedDescription":"NULL","industryName":"Steel-Producers","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3099314,"instrumentType":"Stock","symbol":"GALLANTT","usSymbol":"GNT.IN","name":"Gallantt Metal","traditionalChineseName":"Gallantt Metal","localName":"Gallantt Metal","active":"true","formattedDescription":"NULL","industryName":"Steel-Producers","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3114080,"instrumentType":"Stock","symbol":"GALLISPAT","usSymbol":"LNT.IN","name":"Gallantt Ispat","traditionalChineseName":"Gallantt Ispat","localName":"Gallantt Ispat","active":"true","formattedDescription":"NULL","industryName":"Steel-Producers","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3101649,"instrumentType":"Stock","symbol":"GAMMNINFRA","usSymbol":"GIP.IN","name":"Gammon Infr.Prjs.","traditionalChineseName":"Gammon Infr.Prjs.","localName":"Gammon Infr.Prjs.","active":"true","formattedDescription":"NULL","industryName":"Bldg-Heavy Construction","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3022669,"instrumentType":"Stock","symbol":"GAMMONIND","usSymbol":"GMI.IN","name":"Gammon India","traditionalChineseName":"Gammon India","localName":"Gammon India","active":"true","formattedDescription":"NULL","industryName":"Bldg-Heavy Construction","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3022670,"instrumentType":"Stock","symbol":"GANDHITUBE","usSymbol":"GST.IN","name":"Gandhi Special Tubes","traditionalChineseName":"Gandhi Special Tubes","localName":"Gandhi Special Tubes","active":"true","formattedDescription":"NULL","industryName":"Metal Proc & Fabrication","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3028692,"instrumentType":"Stock","symbol":"GANECOS","usSymbol":"GPX.IN","name":"Ganesha Ecosphere","traditionalChineseName":"Ganesha Ecosphere","localName":"Ganesha Ecosphere","active":"true","formattedDescription":"NULL","industryName":"Apparel-Clothing Mfg","countryCode":104,"exchange":"NSE"}]}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * responseHeader : {"error":false,"errorCode":null,"errorShortDesc":null,"asOfDate":1542714645429,"errorLongDesc":null,"fromCache":false,"cacheType":"t60Cache"}
         * results : [{"RowNumber":3,"instrumentId":3022906,"instrumentType":"Stock","symbol":"GABRIEL","usSymbol":"GAB.IN","name":"Gabriel India","traditionalChineseName":"Gabriel India","localName":"Gabriel India","active":"true","formattedDescription":"NULL","industryName":"Auto/Truck-Original Eqp","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3028705,"instrumentType":"Stock","symbol":"GAEL","usSymbol":"GAE.IN","name":"Gujarat Ambuja Exports","traditionalChineseName":"Gujarat Ambuja Exports","localName":"Gujarat Ambuja Exports","active":"true","formattedDescription":"NULL","industryName":"Food-Packaged","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3022903,"instrumentType":"Stock","symbol":"GAIL","usSymbol":"GAI.IN","name":"Gail (India)","traditionalChineseName":"Gail (India)","localName":"Gail (India)","active":"true","formattedDescription":"NULL","industryName":"Utility-Gas Distribution","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3114376,"instrumentType":"Stock","symbol":"GAL","usSymbol":"YSG.IN","name":"Gyscoal Alloys","traditionalChineseName":"Gyscoal Alloys","localName":"Gyscoal Alloys","active":"true","formattedDescription":"NULL","industryName":"Steel-Producers","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3099314,"instrumentType":"Stock","symbol":"GALLANTT","usSymbol":"GNT.IN","name":"Gallantt Metal","traditionalChineseName":"Gallantt Metal","localName":"Gallantt Metal","active":"true","formattedDescription":"NULL","industryName":"Steel-Producers","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3114080,"instrumentType":"Stock","symbol":"GALLISPAT","usSymbol":"LNT.IN","name":"Gallantt Ispat","traditionalChineseName":"Gallantt Ispat","localName":"Gallantt Ispat","active":"true","formattedDescription":"NULL","industryName":"Steel-Producers","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3101649,"instrumentType":"Stock","symbol":"GAMMNINFRA","usSymbol":"GIP.IN","name":"Gammon Infr.Prjs.","traditionalChineseName":"Gammon Infr.Prjs.","localName":"Gammon Infr.Prjs.","active":"true","formattedDescription":"NULL","industryName":"Bldg-Heavy Construction","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3022669,"instrumentType":"Stock","symbol":"GAMMONIND","usSymbol":"GMI.IN","name":"Gammon India","traditionalChineseName":"Gammon India","localName":"Gammon India","active":"true","formattedDescription":"NULL","industryName":"Bldg-Heavy Construction","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3022670,"instrumentType":"Stock","symbol":"GANDHITUBE","usSymbol":"GST.IN","name":"Gandhi Special Tubes","traditionalChineseName":"Gandhi Special Tubes","localName":"Gandhi Special Tubes","active":"true","formattedDescription":"NULL","industryName":"Metal Proc & Fabrication","countryCode":104,"exchange":"NSE"},{"RowNumber":3,"instrumentId":3028692,"instrumentType":"Stock","symbol":"GANECOS","usSymbol":"GPX.IN","name":"Ganesha Ecosphere","traditionalChineseName":"Ganesha Ecosphere","localName":"Ganesha Ecosphere","active":"true","formattedDescription":"NULL","industryName":"Apparel-Clothing Mfg","countryCode":104,"exchange":"NSE"}]
         */

        private ResponseHeaderBean responseHeader;
        private List<ResultsBean> results;

        public ResponseHeaderBean getResponseHeader() {
            return responseHeader;
        }

        public void setResponseHeader(ResponseHeaderBean responseHeader) {
            this.responseHeader = responseHeader;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResponseHeaderBean {
            /**
             * error : false
             * errorCode : null
             * errorShortDesc : null
             * asOfDate : 1542714645429
             * errorLongDesc : null
             * fromCache : false
             * cacheType : t60Cache
             */

            private boolean error;
            private Object errorCode;
            private Object errorShortDesc;
            private long asOfDate;
            private Object errorLongDesc;
            private boolean fromCache;
            private String cacheType;

            public boolean isError() {
                return error;
            }

            public void setError(boolean error) {
                this.error = error;
            }

            public Object getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(Object errorCode) {
                this.errorCode = errorCode;
            }

            public Object getErrorShortDesc() {
                return errorShortDesc;
            }

            public void setErrorShortDesc(Object errorShortDesc) {
                this.errorShortDesc = errorShortDesc;
            }

            public long getAsOfDate() {
                return asOfDate;
            }

            public void setAsOfDate(long asOfDate) {
                this.asOfDate = asOfDate;
            }

            public Object getErrorLongDesc() {
                return errorLongDesc;
            }

            public void setErrorLongDesc(Object errorLongDesc) {
                this.errorLongDesc = errorLongDesc;
            }

            public boolean isFromCache() {
                return fromCache;
            }

            public void setFromCache(boolean fromCache) {
                this.fromCache = fromCache;
            }

            public String getCacheType() {
                return cacheType;
            }

            public void setCacheType(String cacheType) {
                this.cacheType = cacheType;
            }
        }

        public static class ResultsBean {
            /**
             * RowNumber : 3
             * instrumentId : 3022906
             * instrumentType : Stock
             * symbol : GABRIEL
             * usSymbol : GAB.IN
             * name : Gabriel India
             * traditionalChineseName : Gabriel India
             * localName : Gabriel India
             * active : true
             * formattedDescription : NULL
             * industryName : Auto/Truck-Original Eqp
             * countryCode : 104
             * exchange : NSE
             */

            private int RowNumber;
            private int instrumentId;
            private String instrumentType;
            private String symbol;
            private String usSymbol;
            private String name;
            private String traditionalChineseName;
            private String localName;
            private String active;
            private String formattedDescription;
            private String industryName;
            private int countryCode;
            private String exchange;

            public int getRowNumber() {
                return RowNumber;
            }

            public void setRowNumber(int RowNumber) {
                this.RowNumber = RowNumber;
            }

            public int getInstrumentId() {
                return instrumentId;
            }

            public void setInstrumentId(int instrumentId) {
                this.instrumentId = instrumentId;
            }

            public String getInstrumentType() {
                return instrumentType;
            }

            public void setInstrumentType(String instrumentType) {
                this.instrumentType = instrumentType;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public String getUsSymbol() {
                return usSymbol;
            }

            public void setUsSymbol(String usSymbol) {
                this.usSymbol = usSymbol;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTraditionalChineseName() {
                return traditionalChineseName;
            }

            public void setTraditionalChineseName(String traditionalChineseName) {
                this.traditionalChineseName = traditionalChineseName;
            }

            public String getLocalName() {
                return localName;
            }

            public void setLocalName(String localName) {
                this.localName = localName;
            }

            public String getActive() {
                return active;
            }

            public void setActive(String active) {
                this.active = active;
            }

            public String getFormattedDescription() {
                return formattedDescription;
            }

            public void setFormattedDescription(String formattedDescription) {
                this.formattedDescription = formattedDescription;
            }

            public String getIndustryName() {
                return industryName;
            }

            public void setIndustryName(String industryName) {
                this.industryName = industryName;
            }

            public int getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(int countryCode) {
                this.countryCode = countryCode;
            }

            public String getExchange() {
                return exchange;
            }

            public void setExchange(String exchange) {
                this.exchange = exchange;
            }
        }
    }
}
