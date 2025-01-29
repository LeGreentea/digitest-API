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
@DiscriminatorValue("ESSAY")
public class EssayQuestion extends Question {

    @Column(nullable = false)
    private String answer;

    @Override
    public String getAnswer() {
        return answer;
    }
}