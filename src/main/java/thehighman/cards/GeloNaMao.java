package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GeloNaMao extends BaseCard {
    public static final String ID = makeID("GeloNaMao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 3;

    public GeloNaMao() {
        super(ID, info);
        setDamage(DAMAGE);
        this.exhaust = false;
        setDamage(DAMAGE, 2); // 3 → 5 de dano com upgrade

        this.keywords.add("bad trip");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));

        if (upgraded) {
            addToBot(new com.megacrit.cardcrawl.actions.common.ExhaustAction(
                    p, p, 1, false
            ));
        } else {
            List<AbstractCard> validCards = p.hand.group.stream()
                    .filter(c -> c != this)
                    .collect(Collectors.toList());
            if (!validCards.isEmpty()) {
                Collections.shuffle(validCards);
                AbstractCard toExhaust = validCards.get(0);
                addToBot(new ExhaustSpecificCardAction(toExhaust, p.hand));
            }
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2); // 3 → 5
            initializeDescription();
        }
    }
}