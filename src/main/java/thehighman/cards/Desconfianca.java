package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Larica;
import thehighman.util.CardStats;

public class Desconfianca extends BaseCard {
    public static final String ID = makeID("Desconfianca");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;
    private static final int LARICA_AMOUNT = 1;

    public Desconfianca() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha bloqueio
        addToBot(new GainBlockAction(p, this.block));

        // Aplica Larica ao inimigo
        addToBot(new ApplyPowerAction(m, p, new Larica(m, LARICA_AMOUNT), LARICA_AMOUNT));
    }
}