package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.LaricaPower;
import thehighman.util.CardStats;

public class SacolaDeGuloseimas extends BaseCard {
    public static final String ID = makeID("SacolaDeGuloseimas");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int BONUS_DAMAGE = 5;

    public SacolaDeGuloseimas() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.isMultiDamage = true;
        this.rawDescription = "Cause !D! de dano a um inimigo. Ganhe 1 de Comido. Se ele tiver Larica, cause 5 de dano a todos os inimigos.";
        this.keywords.add("comido");
        this.keywords.add("larica");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Dano direto ao inimigo alvo
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        // Ganha 1 stack de Comido
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, 1), 1));

        // Se o inimigo tiver Larica, causa 5 de dano a todos os inimigos
        if (m.hasPower(LaricaPower.POWER_ID)) {
            addToBot(new DamageAllEnemiesAction(p,
                    DamageInfo.createDamageMatrix(BONUS_DAMAGE, true),
                    DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE); // 10 → 14 de dano
            initializeDescription();
        }
    }
}