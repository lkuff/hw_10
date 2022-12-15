package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class RegFormTest extends TestBase{
    @BeforeAll
    static void beforeAll() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
    }

    @Test
    void fillFormTest() {
        step("Open registrations form", () -> {
            open("/automation-practice-form");
            executeJavaScript("$('footer').remove()");
            executeJavaScript("$('#fixedban').remove()");
        });
        step("Fill form", () -> {
            $("#firstName").setValue("Alexey");
            $("#lastName").setValue("Nikiforov");
            $("#userEmail").setValue("some@mail.com");
            $(byText("Male")).click();
            $("#userNumber").setValue("1231231234");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("September");
            $(".react-datepicker__year-select").selectOption("1993");
            $(".react-datepicker__day--004").click();
            $("#subjectsInput").setValue("Computer Science").pressEnter();
            $(byText("Reading")).click();
            $(byText("Music")).click();
            $("#uploadPicture").uploadFile(new File("src/test/resources/1.jpg"));
            $("#currentAddress").setValue("Russia, Moscow");
            $("#state").click();
            $(byText("Haryana")).click();
            $("#city").click();
            $(byText("Panipat")).click();
            $("#submit").click();
        });


        step("Check results", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(text("Alexey Nikiforov"));
            $(".table-responsive").shouldHave(text("some@mail.com"));
            $(".table-responsive").shouldHave(text("Male"));
            $(".table-responsive").shouldHave(text("1231231234"));
            $(".table-responsive").shouldHave(text("04 September,1993"));
            $(".table-responsive").shouldHave(text("Computer Science"));
            $(".table-responsive").shouldHave(text("Reading, Music"));
            $(".table-responsive").shouldHave(text("1.jpg"));
            $(".table-responsive").shouldHave(text("Russia, Moscow"));
            $(".table-responsive").shouldHave(text("Haryana Panipat"));
        });
    }
}