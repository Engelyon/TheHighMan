package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.cards.BadTrip;
import thehighman.util.CardStats;

public class Confusao extends BaseCard {
    public static final String ID = makeID("Confusao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 5;

    public Confusao() {
        super(ID, info);
        setDamage(DAMAGE);
        this.keywords.add("bad trip");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa 5 de dano
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));

        // Adiciona 1 Bad Trip na pilha de descarte
        addToBot(new MakeTempCardInDiscardAction(new BadTrip(), 1));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3); // 5 → 8 de dano
            initializeDescription();
        }
    }
}