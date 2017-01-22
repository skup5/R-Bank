package org.zelenikr.pia.bankcode;

import org.zelenikr.pia.utils.CSVFileReader;
import org.zelenikr.pia.utils.CSVLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Zelenik
 */
public class CSVBankCodeManager implements BankCodeManager {

    private CSVLoader loader;
    private String source;
    private BankCode localBankCode;
    private List<BankCode> bankCodeList;

    public CSVBankCodeManager(String source, BankCode localBankCode, CSVLoader loader) {
        this.source = source;
        this.localBankCode = localBankCode;
        this.loader = loader;
        this.bankCodeList = load(source);
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

    private List<BankCode> load(String source) {
        List<BankCode> codes = new ArrayList<>();
        try(CSVFileReader reader = loader.getReader(source)) {
            String[] row;
            // headers
            reader.nextRow();
            // data rows
            while (reader.hasNextRow()) {
                row = reader.nextRow();
                codes.add(new BankCode(row[0], row[1]));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
//        codes.add(new BankCode("0100", "Komerční banka, a.s."));
//        codes.add(new BankCode("0300", "Československá obchodní banka, a.s."));
        return codes;
    }
}
