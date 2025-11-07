package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Larica;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

public class Reflexao extends BaseCard {
    public static final String ID = makeID("Reflexao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int LARICA_AMOUNT = 2;

    public Reflexao() {
        super(ID, info);
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Remove todos os stacks de Chapado do inimigo
        if (m.hasPower(Chapado.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(m, p, Chapado.POWER_ID));
        }

        // Aplica 2 de Larica
        addToBot(new ApplyPowerAction(m, p, new Larica(m, LARICA_AMOUNT), LARICA_AMOUNT));
    }
}