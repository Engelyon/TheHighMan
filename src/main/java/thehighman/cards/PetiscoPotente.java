package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.EnergiaExtraProximoTurnoPower;
import thehighman.util.CardStats;

public class PetiscoPotente extends BaseCard {
    public static final String ID = makeID("PetiscoPotente");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_GAIN = 1;
    private static final int COMIDO_THRESHOLD = 5;
    private static final int ENERGY_NEXT_TURN = 2;

    public PetiscoPotente() {
        super(ID, info);
        setMagic(COMIDO_GAIN, 1); // upgrade aumenta Comido de 1 → 2

        this.rawDescription = "Ganhe !M! de Comido. Se tiver 5 ou mais de Comido, ganhe 2 de energia no próximo turno.";
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Ganha 1 de Comido
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, COMIDO_GAIN), COMIDO_GAIN));

        // Se tiver 5 ou mais de Comido, prepara 2 de energia pro próximo turno
        if (p.hasPower(ComidoPower.POWER_ID) && p.getPower(ComidoPower.POWER_ID).amount >= COMIDO_THRESHOLD) {
            addToBot(new ApplyPowerAction(p, p, new EnergiaExtraProximoTurnoPower(p, ENERGY_NEXT_TURN), ENERGY_NEXT_TURN));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); // Comido: 1 → 2
            initializeDescription();
        }
    }
}