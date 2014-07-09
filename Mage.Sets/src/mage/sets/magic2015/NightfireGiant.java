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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class NightfireGiant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Mountain");

    static {
        filter.add(new SubtypePredicate("Mountain"));

    }

    public NightfireGiant(UUID ownerId) {
        super(ownerId, 109, "Nightfire Giant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "M15";
        this.subtype.add("Zombie");
        this.subtype.add("Giant");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Nightfire Giant gets +1/+1 as long as you control a Mountain.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));

        // {4}{R}: Nightfire Giant deals 2 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl("{4}{R}"));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

    }

    public NightfireGiant(final NightfireGiant card) {
        super(card);
    }

    @Override
    public NightfireGiant copy() {
        return new NightfireGiant(this);
    }
}