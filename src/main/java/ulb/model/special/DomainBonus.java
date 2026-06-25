package ulb.model.special;

import ulb.model.domain.status.Stat;

/**
 * Represents a stat bonus granted when a domain expansion is activated.
 * multiplier will typically be a stat multiplier (ex: x1.5)
 */
public record DomainBonus(Stat stat, int modifier) {
}
