package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

public class Ponderar extends BaseCard {
    public static final String ID = makeID("Ponderar");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 15;
    private static final int DISCARD = 2;
    private static final int DRAW = 1;

    public Ponderar() {
        super(ID, info);
        setBlock(BLOCK);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Descartar 2 cartas da mão
        addToBot(new DiscardAction(p, p, DISCARD, false));

        // Comprar 1 carta
        addToBot(new DrawCardAction(DRAW));

        // Ganhar 15 de bloqueio
        addToBot(new GainBlockAction(p, this.block));
    }
}