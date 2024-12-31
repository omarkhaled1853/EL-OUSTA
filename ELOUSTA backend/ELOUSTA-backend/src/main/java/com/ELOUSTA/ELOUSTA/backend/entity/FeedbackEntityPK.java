package com.ELOUSTA.ELOUSTA.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Getter
@NoArgsConstructor
public class FeedbackEntityPK implements Serializable {

    private int clientId;

    private int techId;

    public FeedbackEntityPK(int clientId, int techId) {
        this.clientId = clientId;
        this.techId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedbackEntityPK feedbackEntityPK)) return false;
        return clientId == feedbackEntityPK.clientId && techId == feedbackEntityPK.techId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, techId);
    }
}
