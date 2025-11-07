package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class AlmocoDeGraca extends BaseCard {
    public static final String ID = makeID("AlmocoDeGraca");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_GAIN = 2;
    private static final int ENERGY_GAIN = 2;
    private static final int COMIDO_MAX = 10; // ajuste conforme o limite real

    public AlmocoDeGraca() {
        super(ID, info);
        this.rawDescription = "Ganha 2 de Comido. Se estiver com 10 de Comido, ganhe 2 de Energia.";
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 2 de Comido
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, COMIDO_GAIN), COMIDO_GAIN));

        // Se Comido estiver no máximo, ganha 2 de energia
        if (p.hasPower(ComidoPower.POWER_ID)) {
            int atual = p.getPower(ComidoPower.POWER_ID).amount;
            if (atual >= COMIDO_MAX) {
                addToBot(new GainEnergyAction(ENERGY_GAIN));
            }
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(0); // Reduz o custo de 1 para 0
            initializeDescription();
        }
    }
}