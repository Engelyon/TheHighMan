package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

public class Tapa extends BaseCard {
    public static final String ID = makeID("Tapa");
    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    public Tapa() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CardTags.STRIKE);
        this.keywords.add("chapado"); // minúsculo para ativar tooltip
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Usa baseDamage para permitir que Powers como Seda modifiquem o dano
        DamageInfo info = new DamageInfo(p, this.baseDamage, DamageInfo.DamageType.NORMAL);
        addToBot(new DamageAction(m, info, AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        // Aplica 1 de Chapado ao inimigo
        addToBot(new ApplyPowerAction(m, p, new Chapado(m, 1), 1));
    }
}