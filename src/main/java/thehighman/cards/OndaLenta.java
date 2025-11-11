package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.ChapadoProximoTurnoPower;
import thehighman.util.CardStats;

public class OndaLenta extends BaseCard {
    public static final String ID = makeID("OndaLenta");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int CHAPADO_AMOUNT = 1;

    public OndaLenta() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT);
        this.keywords.add("chapado");
        this.exhaust = true;
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(CHAPADO_AMOUNT); // 1 de Chapado
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Dano direto
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        // Chapado imediato
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));

        // Chapado nos próximos turnos
        int delayedTurns = upgraded ? 2 : 1;
        addToBot(new ApplyPowerAction(m, p, new ChapadoProximoTurnoPower(m, delayedTurns, this.magicNumber), this.magicNumber));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 8 → 11
            initializeDescription();
        }
    }
}