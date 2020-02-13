package io.finbook.controller;

import io.finbook.model.SignData;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.util.Collections;

public class Signer {

    private final SignData signData;

    public Signer(SignData signData) {
        this.signData = signData;
    }

    public void sign() {
        byte[] text = signData.getTextToSign().getBytes();

        try {
            DigestCalculatorProvider digestCalculatorProvider = new JcaDigestCalculatorProviderBuilder().setProvider("BC").build();
            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(signData.getPrivateKey());
            SignerInfoGenerator signerInfoGenerator = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider).build(contentSigner, signData.getCertificate());

            CMSSignedDataGenerator cmsGenerator = new CMSSignedDataGenerator();
            cmsGenerator.addSignerInfoGenerator(signerInfoGenerator);

            cmsGenerator.addCertificates(new JcaCertStore(Collections.singletonList(signData.getCertificate())));

            CMSTypedData cmsData = new CMSProcessableByteArray(text);
            CMSSignedData cms = cmsGenerator.generate(cmsData, true);

            signData.setSign(cms.getEncoded());
        } catch (OperatorCreationException | CertificateEncodingException | CMSException | IOException ignored) {
            ignored.printStackTrace();
        }
    }
}