package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

public class MarDeFumaca extends BaseCard {
    public static final String ID = makeID("MarDeFumaca");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 3;
    private static final int BLOCK_PER_STACK = 3;

    public MarDeFumaca() {
        super(ID, info);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalStacks = 0;

        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new Chapado(mo, CHAPADO_AMOUNT), CHAPADO_AMOUNT));
                totalStacks += CHAPADO_AMOUNT;
            }
        }

        // Ganha bloqueio proporcional ao total de stacks aplicados
        int totalBlock = totalStacks * BLOCK_PER_STACK;
        addToBot(new GainBlockAction(p, totalBlock));
    }
}