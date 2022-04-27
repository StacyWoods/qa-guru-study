package pages.components;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {
    public void setDate(String day, String month, String year) {
        var dayPicker = ".react-datepicker__day--0";
        var dayInt = Integer.valueOf(day);
        if (dayInt < 10) {
            dayPicker = ".react-datepicker__day--00";
        }
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(year);
        $(dayPicker + dayInt.toString() +
                ":not(.react-datepicker__day--outside-month)").click();
    }
}