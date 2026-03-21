package com.example.demo01.domains.MiniCrm.Utils;

import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtilImpl;
import com.example.demo01.core.Basement.repository.BranchUnitRepository;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.MiniCrm.Contract.dtos.contractUtils.CalPriceRequest;
import com.example.demo01.utils.AppUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class MC_Utils {


    private final MongoTemplate mongoTemplate;

    private final SecurityRepoUtilImpl securityRepoUtil;

    private final BranchUnitRepository branchUnitRepository;

    private final AppUtil appUtil;

    private final ObjectMapper objectMapper;

    private final CacheManager cacheManager;

    public int countUniqueMonths(LocalDate startDate, int usingDays) {
        LocalDate endDate = startDate.plusDays(usingDays).minusDays(1);
        Set<YearMonth> uniqueMonths = new HashSet<>();

        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            uniqueMonths.add(YearMonth.from(current));
            current = current.plusDays(1);
        }

        return uniqueMonths.size();
    }

    public String handleSplitValue(Double inputValue) {
        if (inputValue == null) {
            return "0";
        }
        double dividerValue = inputValue >= 1000000.0 ? 1000000.0 : 1000.0;
        String thousandFormat = inputValue >= 1000000.0 ? "M" : "K";

        double shortResult = inputValue / dividerValue;

        DecimalFormat df = new DecimalFormat("#.#");

        return df.format(shortResult) + thousandFormat;
    }

    public double calTotalValue(LocalDate startDate, LocalDate endDate, double pricePerUnit, @NotNull String unit) {
        int dayDuration = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int durationValue = 0;
        double totalValue = 0;

        switch (unit) {
            case "W":
                durationValue = (int) Math.ceil((double) dayDuration / 7);
                totalValue = durationValue * pricePerUnit;
                break;
            case "D":
                totalValue = dayDuration * pricePerUnit;
                break;
            default:
                LocalDate nextCalendarMonth = LocalDate.from(startDate.withDayOfMonth(1).plusMonths(1));

                LocalDate nextMonth = nextCalendarMonth.isBefore(endDate) ? nextCalendarMonth : endDate;
                LocalDate endMonthFirst = endDate.minusDays(endDate.getDayOfMonth() - 1);

                int DaysStartMonth = startDate.lengthOfMonth();
                int monthDuration = countUniqueMonths(startDate, dayDuration);

                int usingDayAtStartMonth = startDate.lengthOfMonth() - startDate.getDayOfMonth() + 1;

                int daysEndMonth = endDate.lengthOfMonth();
                int DaysFirst = (int) Math.min(usingDayAtStartMonth, dayDuration);

                int DaysLast = monthDuration > 1 ? endDate.getDayOfMonth() : 0;

                int endDaysLast = (endDate.getDayOfMonth() == daysEndMonth || startDate.getDayOfMonth() == 1) ? daysEndMonth : DaysStartMonth;

                int fullMonths = Math.max(0, (int) ChronoUnit.MONTHS.between(nextMonth, endMonthFirst));

                totalValue = Math.round(pricePerUnit * ((double) DaysFirst / DaysStartMonth) + pricePerUnit * fullMonths + pricePerUnit * Math.min(
                        1, (double) DaysLast / endDaysLast
                ));
        }

        return totalValue;
    };

    public double calPrice(CalPriceRequest calPriceRequest) {
        double factor = 100000.0;

        Double calculationPrice = calPriceRequest.getCalculationPrice();
        Double exchangeRate = calPriceRequest.getExchangeRate();
        Double usingNumber = calPriceRequest.getUsingNumber();
        String productTag = calPriceRequest.getProductTag();
        Double calculateRule = calPriceRequest.getCalculateRule();
        String unit = calPriceRequest.getUnit();


        if (exchangeRate == null || exchangeRate <0) { exchangeRate = 1.0; }
        double handlePrice;

        switch (productTag) {
            case "Long-Term":
                double standardPrice = Math.ceil((usingNumber * calculationPrice * 1.1) / factor) * factor;
                handlePrice = Math.ceil((standardPrice * calculateRule)) ;
                break;
            case "Service":
                handlePrice = calculateRule;
                break;
            case "Hot Desk":
                if (unit.equals("M")) {
                    handlePrice = Math.ceil((calculationPrice * calculateRule) / exchangeRate) * usingNumber;
                } else  {
                    handlePrice = calculateRule * usingNumber;
                }
                break;
            default:
                handlePrice = 1;
        }
        return Math.ceil(handlePrice / exchangeRate);
    };

    public String contractCodeGen(String buShortName, LocalDate beginDate, int contractNumber) {
        String buValidName = branchUnitRepository.findByBuId(buShortName).getAccountantCode();
        if (buValidName == null || buValidName.isEmpty()) {
            throw new ResourceNotFoundException("Contract", "buShortName", buShortName);
        }
        DateTimeFormatter customFormater = DateTimeFormatter.ofPattern("yyyyMMdd");
        DecimalFormat numberFormat = new DecimalFormat("0000");
        return String.format("MINDX-%s-%s-%s",
                buValidName,
                beginDate.format(customFormater),
                numberFormat.format(contractNumber)
        );
    };

    public String invoiceCodeGen(String serviceId, String buId, Double exportValue, String customerId) {

        String exportAmountShortName = handleSplitValue(exportValue);
        String shortCustomerId = appUtil.handleSubString(customerId, 3, false);
        String serviceShortName = appUtil.handleSubString(serviceId, 3, true);
        String identifyValue = appUtil.handleSubString(UUID.randomUUID().toString(), 3, true);

        return String.format("INV-%s-%s-%s-%s-%s",
                exportAmountShortName,
                serviceShortName,
                buId,
                shortCustomerId,
                identifyValue
        );
    }

    public String appendixCodeGen(String agreementNumber, String contractCode) {
        DecimalFormat numberFormat = new DecimalFormat("0000");
        int AppendixNumber = Integer.parseInt(agreementNumber);

        return String.format("PL%s-%s",
                numberFormat.format(AppendixNumber),
                contractCode
        );
    }

    public String paymentOrderGenTitle(Double paymentAmount , LocalDate paymentDate, String customerId ) {
        String handlePaymentAmount = handleSplitValue(paymentAmount);
        String handlePaymentDate = paymentDate.toString().replace("-","");
        String handleCustomerId = appUtil.handleSubString(customerId, 3, false);
        String randomCode = appUtil.handleSubString(UUID.randomUUID().toString(), 3, true);
        return String.format("PO-%s-%s-%s-%s",
                handleCustomerId,
                handlePaymentDate,
                handlePaymentAmount,
                randomCode
        );
    }

}
