package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

public class ErvaDaPaz extends BaseCard {
    public static final String ID = makeID("ErvaDaPaz");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL,
            2
    );

    private static final int CHAPADO_AMOUNT = 4;
    private static final int BLOCK_AMOUNT = 10;

    public ErvaDaPaz() {
        super(ID, info);
        setBlock(BLOCK_AMOUNT);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Aplica 4 de Chapado e 10 de Bloqueio ao jogador
        addToBot(new ApplyPowerAction(p, p, new Chapado(p, CHAPADO_AMOUNT), CHAPADO_AMOUNT));
        addToBot(new GainBlockAction(p, p, this.block));

        // Aplica 4 de Chapado e 10 de Bloqueio a todos os inimigos
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new Chapado(mo, CHAPADO_AMOUNT), CHAPADO_AMOUNT));
                addToBot(new GainBlockAction(mo, p, this.block));
            }
        }
    }
}