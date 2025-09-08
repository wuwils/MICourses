package com. xiaomi. wusheng. course_0220. ProductStructure;

public class VirtualAttributes {
    private String redeemCode; // 兑换码
    private String validityPeriod; // 有效期
    private String usageRules; // 使用规则

    // Getters and Setters
    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getUsageRules() {
        return usageRules;
    }

    public void setUsageRules(String usageRules) {
        this.usageRules = usageRules;
    }

    @Override
    public String toString() {
        return "VirtualAttributes{" +
                "redeemCode='" + redeemCode + '\'' +
                ", validityPeriod='" + validityPeriod + '\'' +
                ", usageRules='" + usageRules + '\'' +
                '}';
    }
}

