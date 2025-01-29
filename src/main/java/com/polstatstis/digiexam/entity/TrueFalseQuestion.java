package com.polstatstis.digiexam.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("TRUE_FALSE")
public class TrueFalseQuestion extends Question {

    @Column(nullable = false)
    private boolean answer;

    // Manually added getter method for the answer field
    public boolean isAnswer() {
        return answer;
    }

    @Override
    public String getAnswer() {
        return Boolean.toString(answer);
    }
}