package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class ReservaNutricional extends BaseCard {
    public static final String ID = makeID("ReservaNutricional");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_GAIN = 2;
    private static final int ERVA_GAIN = 3;
    private static final int UPG_COMIDO = 1;
    private static final int UPG_ERVA = 2;

    private int ervaAmount;

    public ReservaNutricional() {
        super(ID, info);
        setMagic(COMIDO_GAIN, UPG_COMIDO); // usa magicNumber para Comido
        this.ervaAmount = ERVA_GAIN;
        this.rawDescription = "Ganhe !M! de Comido e " + ervaAmount + " de Erva.";
        this.keywords.add("comido");
        this.keywords.add("erva");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ervaAmount), ervaAmount));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_COMIDO); // Comido: 2 → 3
            this.ervaAmount += UPG_ERVA;    // Erva: 3 → 5
            this.rawDescription = "Ganhe !M! de Comido e " + ervaAmount + " de Erva.";
            initializeDescription();
        }
    }
}