package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class LimparOBong extends BaseCard {
    public static final String ID = makeID("LimparOBong");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public LimparOBong() {
        super(ID, info);
        this.selfRetain = true;
        this.exhaust = true;
        this.keywords.add("erva");
        this.keywords.add("força");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ErvaPower.POWER_ID)) {
            int erva = p.getPower(ErvaPower.POWER_ID).amount;
            if (erva > 0) {
                // consome toda a Erva
                addToBot(new ReducePowerAction(p, p, ErvaPower.POWER_ID, erva));

                // quantidade de força por 1 de Erva: 2 se upada, 1 se não
                int strengthPerErva = upgraded ? 2 : 1;
                int totalStrength = erva * strengthPerErva;

                // aplica Força temporária (Strength + LoseStrength)
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, totalStrength), totalStrength));
                addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, totalStrength), totalStrength));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            initializeDescription();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        if (p == null || !p.hasPower(ErvaPower.POWER_ID) || p.getPower(ErvaPower.POWER_ID).amount <= 0) {
            this.cantUseMessage = "Preciso de pelo menos 1 de Erva.";
            return false;
        }
        return true;
    }
}