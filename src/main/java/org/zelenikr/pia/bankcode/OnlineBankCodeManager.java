package org.zelenikr.pia.bankcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Zelenik
 */
public class OnlineBankCodeManager implements BankCodeManager {

    private String url;
    private BankCode localBankCode;
    private List<BankCode> bankCodeList;

    public OnlineBankCodeManager(String url, BankCode localBankCode) {
        this.url = url;
        this.localBankCode = localBankCode;
        this.bankCodeList = load();
        this.bankCodeList.add(0, localBankCode);
    }

    @Override
    public BankCode getBankCode() {
        return localBankCode;
    }

    @Override
    public BankCode[] getBankCodes() {
        return bankCodeList.toArray(new BankCode[bankCodeList.size()]);
    }

    @Override
    public String getBankName(String bankCode) {
        for(BankCode item : bankCodeList){
            if(item.getCode().equals(bankCode))
                return item.getBankName();
        }
        return "";
    }

    private List<BankCode> load() {
        List<BankCode> codes = new ArrayList<>();
        codes.add(new BankCode("0100", "Komerční banka, a.s."));
        codes.add(new BankCode("0300", "Československá obchodní banka, a.s."));
        return codes;
    }
}
