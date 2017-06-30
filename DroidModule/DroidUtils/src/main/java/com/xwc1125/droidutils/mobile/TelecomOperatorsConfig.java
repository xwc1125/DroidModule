package com.xwc1125.droidutils.mobile;

/**
 *
 * @ClassName TelecomOperatorsConfig
 * @Description 运营商配置
 * @author xwc1125
 * @date 2016年3月7日 上午9:40:22
 */
public class TelecomOperatorsConfig {
    /**
     *
     * @ClassName TELOPR_TYPE
     * @Description 运营商类型：联通：CUCC，0；移动：CMCC，1；电信：CTC，2,UNKNOW:-1
     * @author xwc1125
     * @date 2016年3月7日 上午9:42:09
     */
    public enum TELOPR_TYPE {
        /**
         * 联通：0
         */
        CUCC {
            @Override
            public String getValue() {
                return "CUCC";
            }

            @Override
            public int getIndex() {
                return 0;
            }
        },
        /**
         * 移动：1
         */
        CMCC {
            @Override
            public String getValue() {
                return "CMCC";
            }

            @Override
            public int getIndex() {
                return 1;
            }
        },
        /**
         * 电信：2
         */
        CTC {
            @Override
            public String getValue() {
                return "CTC";
            }

            @Override
            public int getIndex() {
                return 2;
            }
        },
        /**
         * 未知运营商：-1
         */
        UNKNOW {
            @Override
            public String getValue() {
                return "UNKNOW";
            }

            @Override
            public int getIndex() {
                return -1;
            }
        };
        public abstract String getValue();

        public abstract int getIndex();
    }
}
