package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

public class Analisar extends BaseCard {
    public static final String ID = makeID("Analisar");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int DRAW_AMOUNT = 2;
    private static final int STRENGTH_GAIN = 1;
    private static final int CHAPADO_THRESHOLD = 5;

    public Analisar() {
        super(ID, info);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Compra 2 cartas
        addToBot(new DrawCardAction(p, DRAW_AMOUNT));

        // Verifica se algum inimigo tem 5 ou mais de Chapado
        boolean chapadoDetectado = AbstractDungeon.getCurrRoom().monsters.monsters.stream()
                .anyMatch(mo -> mo.hasPower(Chapado.POWER_ID) && mo.getPower(Chapado.POWER_ID).amount >= CHAPADO_THRESHOLD);

        // Se sim, ganha 1 de Força
        if (chapadoDetectado) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_GAIN), STRENGTH_GAIN));
        }
    }
}