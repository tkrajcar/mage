/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class JeskaiCharm extends CardImpl {

    public JeskaiCharm(UUID ownerId) {
        super(ownerId, 181, "Jeskai Charm", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}{R}{W}");
        this.expansionSetCode = "KTK";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setWhite(true);

        // Choose one -
        // - Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // - Jeskai Charm deals 4 damage to target opponent.
        Mode mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(4));
        mode.getTargets().add(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
        // - Creatures you control get +1/+1 and gain lifelink until end of turn.
        mode = new Mode();
        Effect effect = new BoostControlledEffect(1,1, Duration.EndOfTurn);
        effect.setText("Creatures you control get +1/+1");
        mode.getEffects().add(effect);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent());
        effect.setText("and gain lifelink until end of turn");
        mode.getEffects().add(effect);
        this.getSpellAbility().addMode(mode);
    }

    public JeskaiCharm(final JeskaiCharm card) {
        super(card);
    }

    @Override
    public JeskaiCharm copy() {
        return new JeskaiCharm(this);
    }
}
