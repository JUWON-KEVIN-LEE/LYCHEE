package com.lychee.util;

import com.lychee.data.model.Expenditure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  불완전할 것 같다...
 *  다시 만들어야 할 것 !!
 */
public class SmsParcer {

    List<String> splits; // 분해된 메시지

    Expenditure expenditure; // Exp 객체

    String accumulation; // 누적 금액

    boolean nc_appr; // 승인 여부 체크해야하는지 여부
    /**
     * 메시지를 분해하고 expenditure 객체를 초기화한다.
     * @param body 메시지
     */
    public SmsParcer(String body) {
        splits = split(body);
        nc_appr = true;
        this.expenditure = new Expenditure();
    }

    public Expenditure parse(String co) {
        parseIDE(co);
        if(nc_appr) parseAppr();
        removeName();
        parsePrice();
        parseInst();
        parseDateTime();
        parseAccu();
        parseShopName();

        return expenditure;
    }

    /**
     * 단어 단위로 쪼개는 함수
     * @param body
     * @return List<String>
     */
    private List<String> split(String body) {
        splits = new ArrayList<>();

        String[] splits_ = body.split("\n"); // 개행 없애기

        for(String item_ : splits_) { // 빈 공간 없애기
            item_ = item_.replace("(", " ").replace(")", " ");
            String[] splits_ar = item_.split(" ");

            splits.addAll(Arrays.asList(splits_ar));
        }

        // 불필요한 데이터 없애기
        if(splits.contains("[Web발신]")) splits.remove("[Web발신]");

        return splits;
    }

    private void parseIDE(String co) {
        for(int i=0; i<splits.size(); i++) {
            String split = splits.get(i);
            if(containsCo(split, co)) {
                if(!containsIDE(split)) {
                    split += splits.get(i+1);
                    splits.remove(i+1);
                }
                splits.remove(i);

                expenditure.setCardName(containsAppr(split));
                break;
            }
        }
    }

    private void parseAppr() {
        for(int i=0; i<splits.size(); i++) {
            String split = splits.get(i);

            if(split.contains("해외승인")
                    || split.contains("해외취소")) expenditure.setForeign(true);

            if(split.contains("승인")
                    && !split.contains("취소")) {
                expenditure.setApproved(true);
                splits.remove(i);
                break;
            }

            if(split.contains("취소")) {
                expenditure.setApproved(false);
                splits.remove(i);
                break;
            }
        }
    }

    private void removeName() {
        for(int i=0; i<splits.size(); i++) {
            String split = splits.get(i);
            if(split.contains("*")) {
                splits.remove(i);
                break;
            }
        }
    }

    private void parsePrice() {
        if(expenditure.isForeign()) {
            for(int i=0; i<splits.size(); i++) {
                String split = splits.get(i);

                if(split.contains(".") && (split.contains("USD")||split.contains("달러"))) {

                }
            }
        } else {
            for(int i=0; i<splits.size(); i++) {
                String split = splits.get(i);

                if(split.contains(",") && split.contains("원")) {
                    String price = split.substring(0, split.indexOf("원")).replace(",", "");
                    expenditure.setPrice(price);
                    splits.remove(i);
                    break;
                }
            }
        }
    }

    private void parseInst() {
        for(int i=0; i<splits.size(); i++) {
            String split = splits.get(i);

            if(split.contains("일시불")) {
                expenditure.setInstallment(false);
                expenditure.setMonthsOfInstallment(0);
                splits.remove(i);
                break;
            }

            if(split.contains("개월")) {
                expenditure.setInstallment(true);
                expenditure.setMonthsOfInstallment(Integer.parseInt(split.substring(0, split.indexOf("개"))));
                splits.remove(i);
                break;
            }
        }
    }

    private void parseDateTime() {
        for(int i=0; i<splits.size(); i++) {
            String split = splits.get(i);
            if(split.contains("/")) {
                expenditure.setDate(split);
                expenditure.setTime(splits.get(i+1));

                splits.remove(i+1);
                splits.remove(i);
                break;
            }
        }
    }

    /**
     * 누적 금액 파싱
     */
    private void parseAccu() {
        for(int i=0; i<splits.size(); i++) {
            String split = splits.get(i);
            if(split.contains("누적")) {
                if(split.length() == 2) {
                    accumulation = splits.get(i+1).replace(",", "").replace("원", "");
                    splits.remove(i+1);
                    splits.remove(i);
                    break;
                } else {
                    accumulation = split.substring(2).replace(",", "").replace("원", "");
                    splits.remove(i);
                    break;
                }
            }
        }
    }

    public String getAccu() {
        return accumulation;
    }

    private void parseShopName() {
        String shopName = "";

        for(int i=0; i<splits.size(); i++) {
            shopName += splits.get(i);
        }
        expenditure.setShopName(shopName);
        splits = null;
    }

    private boolean containsCo(String split, String co) {
        return (split.contains("카드") || split.contains(co));
    }

    private boolean containsIDE(String split) {
        return split.contains("*") || (split.length() >= 6 && !"KB국민카드".equals(split));
    }

    /**
     * @param split
     * @return
     */
    private String containsAppr(String split) {
        if(split.contains("해외")) {
            expenditure.setForeign(true);
            split.replace("해외", "");
        }

        if(split.contains("승인")
                && !split.contains("취소")
                && !split.contains("거절")) {
            expenditure.setApproved(true);
            nc_appr = false;
            return split.replace("승인", "");
        }

        if(split.contains("취소")) {
            expenditure.setApproved(false);
            nc_appr = false;
            return (split.contains("승인")) ?
                    split.replace("승인취소", "") :
                    split.replace("취소", "");
        }

        /*
        TODO 수신부에서 거절 메시지인지 확인해야 함
        if(split.contains("거절")) {
            return (split.contains("승인")) ?
                    split.replace("승인거절", "") :
                    split.replace("거절", "");
        }
        */
        return split;
    }
}

