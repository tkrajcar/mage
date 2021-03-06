/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author nick.myers
 */
public class ManaSeverance extends CardImpl {
    
    public ManaSeverance(UUID ownerId) {
        super(ownerId, 73, "Mana Severance", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{U}");
        this.expansionSetCode = "TMP";
        
        // Search your library for any number of land cards and remove them from the game.
        // Shuffle your library afterwards.
        this.getSpellAbility().addEffect(new ManaSeveranceEffect());
    }
    
    public ManaSeverance(final ManaSeverance card) {
        super(card);
    }
    
    @Override
    public ManaSeverance copy() {
        return new ManaSeverance(this);
    }
    
}

class ManaSeveranceEffect extends SearchEffect {
    
    public ManaSeveranceEffect() {
        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, new FilterLandCard()), Outcome.Exile);
        this.staticText = "Search your library for any number of land cards and remove them from the game. Shuffle your library afterwards.";
    }
    
    public ManaSeveranceEffect(final ManaSeveranceEffect effect) {
        super(effect);
    }
    
    @Override
    public ManaSeveranceEffect copy() {
        return new ManaSeveranceEffect(this);
    }
    
     @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY);
                        }
                    }
                }
            }
            controller.shuffleLibrary(game);
            return true;

        }
        return false;
    }
}
