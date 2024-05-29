package org.example.doctorservice.domain.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.doctorservice.enumerators.State;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorCreateDTO {

    String login;

    String password;

    String name;

    String surname;

    String lastName;

    String phoneNumber;

    String speciality;

    //    Keyinchalik olib tashlanadi. Yani Microserviceda StackService qo'shilgandan keyin
//    stackListdan doctor idsiga qarab stacklarning idlar ro'yxati kelgach doctorserviceda
//    doctorni save qilayotganda entityga berib yuboriladi
    List<Long> stackId;

    Long polyclinicId;

}
