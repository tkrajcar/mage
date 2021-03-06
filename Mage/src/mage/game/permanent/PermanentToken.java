/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.game.permanent;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentToken extends PermanentImpl {

    protected Token token;

    public PermanentToken(Token token, UUID controllerId, String expansionSetCode, Game game) {
        super(controllerId, controllerId, token.getName());
        this.expansionSetCode = expansionSetCode;
        this.token = token.copy();
        this.token.getAbilities().newId(); // neccessary if token has ability like DevourAbility()
        this.token.getAbilities().setSourceId(objectId);
        this.copyFromToken(this.token, game, false); // needed to have at this time (e.g. for subtypes for entersTheBattlefield replacement effects)
    }

    public PermanentToken(final PermanentToken permanent) {
        super(permanent);
        this.token = permanent.token.copy();
        this.expansionSetCode = permanent.expansionSetCode;
    }

    @Override
    public void reset(Game game) {
        copyFromToken(token, game, true);
        super.reset(game);
    }

    private void copyFromToken(Token token, Game game, boolean reset) {
        this.name = token.getName();
        this.abilities.clear();
        if (reset) {
            this.abilities.addAll(token.getAbilities());
        } else {
            // first time -> create ContinuousEffects only once
            for (Ability ability : token.getAbilities()) {
                this.addAbility(ability, game);
            }
        }
        this.abilities.setControllerId(this.controllerId);
        this.manaCost.clear();
        for (ManaCost cost: token.getManaCost()) {
            this.getManaCost().add(cost.copy());
        }
        this.cardType = token.getCardType();
        this.color = token.getColor().copy();
        this.power.initValue(token.getPower().getValue());
        this.toughness.initValue(token.getToughness().getValue());
        this.supertype = token.getSupertype();
        this.subtype = token.getSubtype();
    }

    @Override
    public boolean moveToZone(Zone zone, UUID sourceId, Game game, boolean flag) {
        if (!game.replaceEvent(new ZoneChangeEvent(this, this.getControllerId(), Zone.BATTLEFIELD, zone))) {
            game.rememberLKI(objectId, Zone.BATTLEFIELD, this);
            if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
                game.setZone(objectId, zone); // needed for triggered dies abilities
                game.fireEvent(new ZoneChangeEvent(this, this.getControllerId(), Zone.BATTLEFIELD, zone));
                game.getState().removeTriggersOfSourceId(this.getId());// if token is gone endless triggered abilities have to be removed
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game) {
        if (!game.replaceEvent(new ZoneChangeEvent(this, sourceId, this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED))) {
            game.rememberLKI(objectId, Zone.BATTLEFIELD, this);
            if (game.getPlayer(controllerId).removeFromBattlefield(this, game)) {
                game.fireEvent(new ZoneChangeEvent(this, sourceId, this.getControllerId(), Zone.BATTLEFIELD, Zone.EXILED));
                return true;
            }
        }
        return false;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public PermanentToken copy() {
        return new PermanentToken(this);
    }

    @Override
    public void addAbility(Ability ability, Game game) {
        if (!abilities.containsKey(ability.getId())) {
            Ability copyAbility = ability.copy();
            copyAbility.setControllerId(controllerId);
            copyAbility.setSourceId(objectId);
            game.getState().addAbility(copyAbility, this);
            abilities.add(copyAbility);
        }
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (getToken().getCopySourceCard() != null) {
            getToken().getCopySourceCard().adjustTargets(ability, game);
        } else {
            super.adjustTargets(ability, game);
        }
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (getToken().getCopySourceCard() != null) {
            getToken().getCopySourceCard().adjustCosts(ability, game);
        } else {
            super.adjustCosts(ability, game);
        }
    }


}
